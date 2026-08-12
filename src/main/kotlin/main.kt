fun calculateCommission(
    cardType: String,        //  карты "MC", "Maestro", "Visa", "Мир", "VK Pay"
    amount: Double,          // сумма перевода
    monthSent: Double = 0.0, // отправлено за месяц
    monthReceived: Double = 0.0, // получено за месяц
    daySent: Double = 0.0,   // отправлено за сутки
    dayReceived: Double = 0.0    // получено за сутки
): String {

        if (cardType == "VK Pay") {
        if (amount > 15000) return "Превышен лимит одного перевода VK Pay (15 000 руб.)"
        if (monthSent + amount > 40000) return "Превышен месячный лимит VK Pay (40 000 руб.)"
    } else {
        if (amount > 150000) return "Превышен лимит одного перевода (150 000 руб.)"
        if (daySent + amount > 150000) return "Превышен суточный лимит отправок (150 000 руб.)"
        if (dayReceived + amount > 150000) return "Превышен суточный лимит получений (150 000 руб.)"
        if (monthSent + amount > 600000) return "Превышен месячный лимит отправок (600 000 руб.)"
        if (monthReceived + amount > 600000) return "Превышен месячный лимит получений (600 000 руб.)"
    }

    val commission = when (cardType) {
        "VK Pay" -> 0.0

        "Visa", "Мир" -> {
            val base = amount * 0.0075
            if (base < 35) 35.0 else base
        }

        "MC", "Maestro" -> {
            val totalMonth = monthSent + amount
            if (amount >= 300 && totalMonth <= 75000) {
                0.0
            } else {
                amount * 0.006 + 20
            }
        }

        else -> return "Неизвестный тип карты: $cardType"
    }

    return "Комиссия: ${"%.2f".format(commission)} руб."
}

fun main() {
    println(calculateCommission("MC", 5000.0))

    println(calculateCommission("MC", 200.0))

    println(calculateCommission("MC", 1000.0, monthSent = 74500.0))

    println(calculateCommission("Visa", 1000.0))

    println(calculateCommission("Visa", 50000.0))

    println(calculateCommission("Мир", 10000.0))

    println(calculateCommission("VK Pay", 5000.0))

    println(calculateCommission("VK Pay", 20000.0))

    println(calculateCommission("Visa", 200000.0))
}