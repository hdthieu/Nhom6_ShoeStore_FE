fetch("/breadcrumb.html")
  .then((response) => response.text())
  .then((data) => {
    document.getElementById("breadcrumb-container").innerHTML = data;

    // Cập nhật tên trang sau khi breadcrumb được tải
    document.getElementById("current-page").textContent = "MyAccount";
  });
function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(html => {
            const rightContent = document.getElementById('rightContent');
            rightContent.innerHTML = html;

            // Gọi lại hàm để gắn sự kiện sau khi tải nội dung
            initializeMyOrders();
        })
        .catch(err => console.error('Lỗi khi tải nội dung:', err));
}

function initializeMyOrders() {
    console.log("Đã tải nội dung MyOrders.");

    const buttons = document.querySelectorAll(".order-item-btn");
    if (buttons.length === 0) {
        console.error("Không tìm thấy nút .order-item-btn nào.");
    } else {
        console.log(`Tìm thấy ${buttons.length} nút order-item-btn.`);

        buttons.forEach(button => {
            button.addEventListener("click", function () {
                const orderId = this.getAttribute("data-order-id");
                console.log("orderId: " + orderId);
                if (orderId) {
                    console.log(`Chuyển hướng tới: /customer/account/view/${orderId}`);
                    window.location.href = `/customer/account/view/${orderId}`;
                } else {
                    console.error("Order ID không tồn tại.");
                }
            });
        });
    }
}
