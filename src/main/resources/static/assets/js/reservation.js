$(document).ready(function () {
    let selectedSeats = [];
    let showId = $("#showId").val();
    // Получаем стоимость сеанса из элемента с id="show-cost"
    const seatCost = parseInt(document.getElementById('show-cost').textContent.replace('₽', '').trim() || 0); // Убираем символ ₽ и пробелы

    // Клик по месту
    $(".seat").click(function () {
        // Игнорируем занятые места
        if ($(this).hasClass("reserved")) return;

        const row = $(this).data("row");
        const seat = $(this).data("seat");
        const seatId = `${row}-${seat}`;

        // Добавляем или убираем выбор
        if ($(this).hasClass("selected")) {
            // Если место уже выбрано, снимаем выбор
            $(this).removeClass("selected");
            selectedSeats = selectedSeats.filter(id => id !== seatId);
            $(`#seat-${seatId}`).remove();
        } else {
            // Если место свободно, добавляем его в выбранные
            $(this).addClass("selected");
            selectedSeats.push(seatId);
            $("#selected-seats-list").append(
                `<li id="seat-${seatId}" class="selected-seats-background">
                    <span>${row} ряд, ${seat} место</span>
                    <span>${seatCost} ₽</span>
                </li>`
            );
        }

        // Обновляем сумму
        const totalCost = selectedSeats.length * seatCost; // Пересчитываем общую стоимость
        $("#total-cost").text(totalCost); // Обновляем на странице
    });

    // Обработка кнопки "Купить"
    $("#confirm-reservation").click(function () {
        if (selectedSeats.length === 0) {
            alert("Пожалуйста, выберите места перед покупкой!");
            return;
        }
        console.log("selectedSeats:", selectedSeats);
        console.log("costPerSeat:", seatCost);
        console.log("showId:", showId);

        $.ajax({
            url: "/reservation",
            type: "POST",
            contentType: 'application/x-www-form-urlencoded',
            data: {
                seatIds: selectedSeats,
                costPerSeat: seatCost,
                showId: showId
            },
            traditional: true,
            success: function (response) {
                alert(response);
                window.location.href = "/";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Выводим ошибки в консоль
                console.log("Error:", textStatus, errorThrown);
                console.log("jqXHR:", jqXHR);
                alert("Ошибка бронирования!");
            }
        });
    });
});
