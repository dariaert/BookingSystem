$(document).ready(function () {
    // Получаем текущую дату в формате YYYY-MM-DD
    const currentDate = new Date().toISOString().split('T')[0];
    // Устанавливаем активной радиокнопку с текущей датой и отправляем запрос на сервер
    $('.date-radio').each(function () {
        const radioDate = $(this).data('date');
        if (radioDate === currentDate) {
            $(this).prop('checked', true); // Делаем радио-кнопку выбранной
            $(this).parent().addClass('selected'); // Применяем стиль
            // Отправляем запрос для текущей даты
            $.ajax({
                url: '/shows/filter',
                type: 'GET',
                data: { date: currentDate },
                success: function (data) {
                    $('#shows').html(data); // Обновляем контент
                },
                error: function () {
                    alert('Не удалось загрузить данные!');
                }
            });
        }
    });
    $('.date-radio').change(function () {
        // Убираем стиль у всех радиокнопок
        $('.tab-schedule-item').removeClass('selected');
        // Добавляем стиль к родительскому элементу выбранной радиокнопки
        $(this).parent().addClass('selected');
        const selectedDate = $(this).data('date');
        $.ajax({
            url: '/shows/filter',
            type: 'GET',
            data: { date: selectedDate },
            success: function (data) {
                $('#shows').html(data);
            },
            error: function () {
                alert('Не удалось загрузить данные!');
            }
        });
    });
});