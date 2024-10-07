// 示例：表单提交前弹出确认框
document.querySelectorAll("form").forEach(function(form) {
    form.addEventListener("submit", function(event) {
        const confirmation = confirm("确定要保存修改吗？");
        if (!confirmation) {
            event.preventDefault();  // 阻止表单提交
        }
    });
});
