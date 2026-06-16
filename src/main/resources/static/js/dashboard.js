document.addEventListener('DOMContentLoaded', async () => {
    checkAuth();
    await loadDashboard();
});

async function loadDashboard() {
    try {
        const profile = await getProfile();
        
        // Display profile info
        const profileContent = document.getElementById('profileContent');
        if (profileContent && profile) {
            profileContent.innerHTML = `
                <p><strong>Name:</strong> ${profile.fullName}</p>
                <p><strong>Email:</strong> ${profile.email}</p>
                <p><strong>Phone:</strong> ${profile.phoneNumber || 'N/A'}</p>
                <p><strong>Account:</strong> ${profile.accountNumber || 'N/A'}</p>
                <p><strong>Address:</strong> ${profile.address || 'N/A'}</p>
            `;
        }
        
        // Get bills and calculate stats
        const bills = await getBills();
        if (bills && bills.content) {
            let outstandingBalance = 0;
            bills.content.forEach(bill => {
                if (bill.status !== 'PAID') {
                    outstandingBalance += bill.balance || bill.amountDue || 0;
                }
            });
            
            document.getElementById('outstandingBalance').textContent = 
                `$${outstandingBalance.toFixed(2)}`;
            document.getElementById('recentBillsCount').textContent = 
                bills.content.length;
        }
        
        // Get payment history
        const payments = await getPaymentHistory();
        if (payments && payments.length) {
            document.getElementById('totalPayments').textContent = 
                payments.length;
        }
    } catch (error) {
        console.error('Error loading dashboard:', error);
    }
}
