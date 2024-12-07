document.addEventListener("DOMContentLoaded", () => {
    const links = document.querySelectorAll(".sidebar ul li a");
    const sections = document.querySelectorAll(".content-section");
    // Восстановить активную ссылку из localStorage, если она есть
    const activeSection = localStorage.getItem("activeSection");
    if (activeSection) {
        // Скрыть все секции
        sections.forEach(section => section.classList.remove("active"));
        // Показать секцию
        document.getElementById(activeSection).classList.add("active");
        // Убрать класс 'active' со всех ссылок
        links.forEach(link => link.classList.remove("active"));
        // Добавить класс 'active' к текущей ссылке
        const activeLink = document.querySelector(`a[data-section='${activeSection}']`);
        if (activeLink) {
            activeLink.classList.add("active");
        }
    }
    // Обработчик для кликов по ссылкам
    links.forEach(link => {
        link.addEventListener("click", event => {
            event.preventDefault();
            const sectionId = link.dataset.section;
            // Скрыть все секции
            sections.forEach(section => section.classList.remove("active"));
            // Показать текущую секцию
            document.getElementById(sectionId).classList.add("active");
            // Убрать класс 'active' со всех ссылок
            links.forEach(link => link.classList.remove("active"));
            // Добавить класс 'active' к текущей ссылке
            link.classList.add("active");
            // Сохранить активный раздел в localStorage
            localStorage.setItem("activeSection", sectionId);
        });
    });
});
