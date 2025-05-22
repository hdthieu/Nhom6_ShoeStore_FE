document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM fully loaded and parsed!"); // Log kiểm tra
  const viewDetailButtons = document.querySelectorAll(".order-item-btn");

  viewDetailButtons.forEach(button => {
    button.addEventListener("click", function () {
      const orderId = this.getAttribute("data-order-id");
      console.log("orderId  " + orderId); // Log giá trị orderId
      if (orderId) {
        window.location.href = `/customer/account/view/${orderId}`;
      }
    });
  });
});

