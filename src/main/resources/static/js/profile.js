document.addEventListener('DOMContentLoaded', async () => {
    checkAuth();
    await loadProfile();
});

async function loadProfile() {
    const container = document.getElementById('profileContainer');
    
    try {
        const profile = await getProfile();
        
        container.innerHTML = `
            <div class="profile-grid">
                <div class="profile-field">
                    <strong>Name:</strong>
                    <span>${profile.fullName}</span>
                </div>
                <div class="profile-field">
                    <strong>Email:</strong>
                    <span>${profile.email}</span>
                </div>
                <div class="profile-field">
                    <strong>Phone:</strong>
                    <span>${profile.phoneNumber || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>Account Number:</strong>
                    <span>${profile.accountNumber || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>City:</strong>
                    <span>${profile.city || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>State:</strong>
                    <span>${profile.state || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>Postal Code:</strong>
                    <span>${profile.postalCode || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>Current Tariff:</strong>
                    <span>${profile.currentTariff?.name || 'N/A'}</span>
                </div>
                <div class="profile-field">
                    <strong>Member Since:</strong>
                    <span>${new Date(profile.createdAt).toLocaleDateString()}</span>
                </div>
            </div>
        `;
    } catch (error) {
        container.innerHTML = `<p>Error loading profile: ${error.message}</p>`;
        showMessage('message', `Error loading profile: ${error.message}`, 'error');
    }
}
