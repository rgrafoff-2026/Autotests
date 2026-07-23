fun calculateCommission(
    cardType: String,        // "MC", "Maestro", "Visa", "Мир", "VK Pay"
    amount: Double,          // сумма перевода
    monthSent: Double = 0.0, // отправлено за месяц
    monthReceived: Double = 0.0, // получено за месяц
    daySent: Double = 0.0,   // отправлено за сутки
    dayReceived: Double = 0.0    // получено за сутки
): String {

    // === 1. Проверка лимитов для VK Pay ===
    if (cardType == "VK Pay") {
        if (amount > 15000) return "Превышен лимит одного перевода VK Pay (15 000 руб.)"
        if (monthSent + amount > 40000) return "Превышен месячный лимит VK Pay (40 000 руб.)"
    } else {
        // === 2. Лимиты для банковских карт ===
        if (amount > 150000) return "Превышен лимит одного перевода (150 000 руб.)"
        if (daySent + amount > 150000) return "Превышен суточный лимит отправок (150 000 руб.)"
        if (dayReceived + amount > 150000) return "Превышен суточный лимит получений (150 000 руб.)"
        if (monthSent + amount > 600000) return "Превышен месячный лимит отправок (600 000 руб.)"
        if (monthReceived + amount > 600000) return "Превышен месячный лимит получений (600 000 руб.)"
    }

    // === 3. Расчёт комиссии ===
    val commission = when (cardType) {
        "VK Pay" -> 0.0

        "Visa", "Мир" -> {
            val base = amount * 0.0075
            if (base < 35) 35.0 else base
        }

        "MC", "Maestro" -> {
            val totalMonth = monthSent + amount
            if (amount >= 300 && totalMonth <= 75000) {
                0.0  // Акция: комиссия 0%
            } else {
                amount * 0.006 + 20  // 0.6% + 20 руб.
            }
        }

        else -> return "Неизвестный тип карты: $cardType"
    }

    return "Комиссия: ${"%.2f".format(commission)} руб."
}

// === Тесты ===
fun main() {
    println(calculateCommission("MC", 5000.0))
    // Акция работает: 0 руб.

    println(calculateCommission("MC", 200.0))
    // Сумма < 300: 200 * 0.006 + 20 = 21.20 руб.

    println(calculateCommission("MC", 1000.0, monthSent = 74500.0))
    // Превышен лимит 75000: 1000 * 0.006 + 20 = 26 руб.

    println(calculateCommission("Visa", 1000.0))
    // 1000 * 0.0075 = 7.5 → минимум 35 руб.

    println(calculateCommission("Visa", 50000.0))
    // 50000 * 0.0075 = 375 руб.

    println(calculateCommission("Мир", 10000.0))
    // 10000 * 0.0075 = 75 руб.

    println(calculateCommission("VK Pay", 5000.0))
    // 0 руб.

    println(calculateCommission("VK Pay", 20000.0))
    // Превышен лимит 15000

    println(calculateCommission("Visa", 200000.0))
    // Превышен лимит 150000
}