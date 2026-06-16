document.addEventListener('DOMContentLoaded', async () => {
    checkAuth();
    await loadBills();
});

async function loadBills() {
    const container = document.getElementById('billsContainer');
    
    try {
        const response = await getBills();
        const bills = response.content || response || [];
        
        if (!bills || bills.length === 0) {
            container.innerHTML = '<p>No bills found.</p>';
            return;
        }
        
        container.innerHTML = bills.map(bill => `
            <div class="bill-item">
                <div class="bill-info">
                    <h3>${bill.billNumber}</h3>
                    <p><strong>Period:</strong> ${bill.billingPeriodStart} to ${bill.billingPeriodEnd}</p>
                    <p><strong>Due Date:</strong> ${bill.dueDate}</p>
                    <p><strong>Units:</strong> ${bill.totalUnitsConsumed || 0}</p>
                </div>
                <div style="text-align: right;">
                    <div class="bill-amount">$${bill.amountDue || 0}</div>
                    <span class="bill-status ${bill.status}">${bill.status}</span>
                    ${bill.status !== 'PAID' ? `
                        <button class="btn btn-success" style="margin-top: 10px; width: 100%;" 
                                onclick="goToPayment(${bill.id})">
                            Pay Now
                        </button>
                    ` : ''}
                </div>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = `<p>Error loading bills: ${error.message}</p>`;
        showMessage('message', `Error loading bills: ${error.message}`, 'error');
    }
}

function goToPayment(billId) {
    sessionStorage.setItem('selectedBillId', billId);
    window.location.href = '/web/payments';
}
