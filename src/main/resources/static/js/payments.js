document.addEventListener('DOMContentLoaded', async () => {
    checkAuth();
    await loadBillsForPayment();
    await loadPaymentHistory();
    
    const paymentForm = document.getElementById('paymentForm');
    if (paymentForm) {
        paymentForm.addEventListener('submit', handlePayment);
        
        const billSelect = document.getElementById('billId');
        billSelect.addEventListener('change', showBillDetails);
    }
});

async function loadBillsForPayment() {
    try {
        const response = await getBills();
        const bills = response.content || response || [];
        
        const billSelect = document.getElementById('billId');
        billSelect.innerHTML = '<option value="">Select a bill...</option>';
        
        bills.filter(bill => bill.status !== 'PAID').forEach(bill => {
            const option = document.createElement('option');
            option.value = bill.id;
            option.textContent = `${bill.billNumber} - $${bill.amountDue} (Due: ${bill.dueDate})`;
            billSelect.appendChild(option);
        });
        
        // Check if there's a pre-selected bill
        const selectedBillId = sessionStorage.getItem('selectedBillId');
        if (selectedBillId) {
            billSelect.value = selectedBillId;
            showBillDetails();
            sessionStorage.removeItem('selectedBillId');
        }
    } catch (error) {
        showMessage('message', `Error loading bills: ${error.message}`, 'error');
    }
}

async function showBillDetails() {
    const billId = document.getElementById('billId').value;
    const detailsDiv = document.getElementById('billDetails');
    
    if (!billId) {
        detailsDiv.innerHTML = '<p>Select a bill to see details</p>';
        return;
    }
    
    try {
        const response = await getBills();
        const bills = response.content || response || [];
        const bill = bills.find(b => b.id == billId);
        
        if (bill) {
            detailsDiv.innerHTML = `
                <p><strong>Bill Number:</strong> ${bill.billNumber}</p>
                <p><strong>Billing Period:</strong> ${bill.billingPeriodStart} to ${bill.billingPeriodEnd}</p>
                <p><strong>Amount Due:</strong> $${bill.amountDue}</p>
                <p><strong>Units Consumed:</strong> ${bill.totalUnitsConsumed}</p>
                <p><strong>Status:</strong> ${bill.status}</p>
            `;
        }
    } catch (error) {
        detailsDiv.innerHTML = `<p>Error loading bill details: ${error.message}</p>`;
    }
}

async function handlePayment(e) {
    e.preventDefault();
    
    const billId = document.getElementById('billId').value;
    const method = document.getElementById('paymentMethod').value;
    
    if (!billId) {
        showMessage('message', 'Please select a bill', 'error');
        return;
    }
    
    try {
        await payBill(parseInt(billId), method);
        showMessage('successMessage', 'Payment processed successfully!', 'success');
        
        setTimeout(async () => {
            await loadBillsForPayment();
            await loadPaymentHistory();
            document.getElementById('paymentForm').reset();
            document.getElementById('billDetails').innerHTML = '<p>Select a bill to see details</p>';
        }, 2000);
    } catch (error) {
        showMessage('message', `Payment failed: ${error.message}`, 'error');
    }
}

async function loadPaymentHistory() {
    try {
        const payments = await getPaymentHistory();
        const historyContainer = document.getElementById('historyContainer');
        
        if (!payments || payments.length === 0) {
            historyContainer.innerHTML = '<p>No payment history</p>';
            return;
        }
        
        historyContainer.innerHTML = payments.map(payment => `
            <div class="payment-item">
                <p><strong>Bill ID:</strong> ${payment.billId}</p>
                <p><strong>Amount:</strong> $${payment.amount}</p>
                <p><strong>Method:</strong> ${payment.paymentMethod}</p>
                <p><strong>Status:</strong> ${payment.status}</p>
                <p><strong>Date:</strong> ${new Date(payment.createdAt).toLocaleDateString()}</p>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading payment history:', error);
    }
}
