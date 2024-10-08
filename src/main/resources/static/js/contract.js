document.addEventListener("DOMContentLoaded", function() {
    const staffSelect = document.getElementById('staffSelect');
    const clientSelect = document.getElementById('clientSelect');

    staffSelect.addEventListener('change', function() {
        const staffId = this.value;
        fetch(`/contracts/clients/${staffId}`)
            .then(response => response.json())
            .then(data => {
                // 清空现有的客户选项
                clientSelect.innerHTML = '<option value="" disabled selected>请选择客户</option>';
                data.forEach(client => {
                    const option = document.createElement('option');
                    option.value = client.id;
                    option.text = client.nickName;
                    clientSelect.appendChild(option);
                });
            });
    });
});
