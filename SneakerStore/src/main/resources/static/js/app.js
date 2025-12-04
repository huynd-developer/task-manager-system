// Custom JavaScript for Sneaker Store

document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.remove();
        }, 5000);
    });

    // Confirm before delete
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Bạn có chắc chắn muốn xóa?')) {
                e.preventDefault();
            }
        });
    });

    // Calculate total price for order details
    function calculateTotal() {
        const quantity = document.getElementById('quantity').value;
        const unitPrice = document.getElementById('unitPrice').value;
        const totalPrice = quantity * unitPrice;
        document.getElementById('totalPrice').value = totalPrice.toFixed(2);
    }

    // Quantity and price event listeners
    const quantityInput = document.getElementById('quantity');
    const unitPriceInput = document.getElementById('unitPrice');

    if (quantityInput) {
        quantityInput.addEventListener('input', calculateTotal);
    }
    if (unitPriceInput) {
        unitPriceInput.addEventListener('input', calculateTotal);
    }
});

// Search functionality
function searchProducts() {
    const input = document.getElementById('searchInput');
    const filter = input.value.toLowerCase();
    const products = document.querySelectorAll('.product-item');

    products.forEach(product => {
        const text = product.textContent.toLowerCase();
        if (text.includes(filter)) {
            product.style.display = '';
        } else {
            product.style.display = 'none';
        }
    });
}