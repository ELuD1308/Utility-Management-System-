// Base API Service
const API_BASE_URL = 'http://localhost:8080';

class AuthService {
    constructor() {
        this.accessToken = localStorage.getItem('accessToken');
        this.refreshToken = localStorage.getItem('refreshToken');
    }

    setTokens(accessToken, refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
    }

    clearTokens() {
        this.accessToken = null;
        this.refreshToken = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    }

    isAuthenticated() {
        return this.accessToken != null;
    }

    getAuthHeader() {
        return {
            'Authorization': `Bearer ${this.accessToken}`,
            'Content-Type': 'application/json'
        };
    }
}

const authService = new AuthService();

// Redirect to login if not authenticated
function checkAuth() {
    if (!authService.isAuthenticated()) {
        window.location.href = '/web/login';
    }
}

// API Methods
async function apiCall(endpoint, method = 'GET', data = null, requiresAuth = false) {
    const options = {
        method: method,
        headers: requiresAuth ? authService.getAuthHeader() : { 'Content-Type': 'application/json' }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
        
        if (response.status === 401) {
            authService.clearTokens();
            window.location.href = '/web/login';
            return null;
        }

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || `HTTP Error: ${response.status}`);
        }

        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        }
        return await response.text();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Auth API calls
async function register(fullName, firstName, lastName, email, password, phoneNumber = '') {
    return apiCall('/api/auth/register', 'POST', {
        fullName,
        firstName,
        lastName,
        email,
        password,
        phoneNumber,
        roles: ['CUSTOMER']
    });
}

async function login(email, password) {
    const response = await apiCall('/api/auth/login', 'POST', { email, password });
    authService.setTokens(response.accessToken, response.refreshToken);
    return response;
}

// Customer API calls
async function getProfile() {
    return apiCall('/api/customer/profile', 'GET', null, true);
}

async function getBills() {
    return apiCall('/api/customer/bills', 'GET', null, true);
}

async function getPaymentHistory() {
    return apiCall('/api/v1/payments/history', 'GET', null, true);
}

// Payment API calls
async function payBill(billId, method = 'direct-debit') {
    const endpoint = method === 'standing-order' 
        ? '/api/v1/payments/standing-order' 
        : '/api/v1/payments/direct-debit';
    return apiCall(endpoint, 'POST', { billId }, true);
}

// Utility function to show messages
function showMessage(elementId, message, type = 'error') {
    const element = document.getElementById(elementId);
    if (element) {
        element.textContent = message;
        element.className = `alert alert-${type} show`;
        
        if (type === 'success') {
            setTimeout(() => {
                element.className = 'alert alert-success';
            }, 3000);
        }
    }
}

// Logout function
function logout() {
    authService.clearTokens();
    window.location.href = '/web/login';
}

// Setup logout buttons
document.addEventListener('DOMContentLoaded', () => {
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', logout);
    }
});
