# AxoCurrency

ВНИМАНИЕ! Это всё так же один из первых плагинов, который был написан мной. Не судите строго. Однако он полностью рабочий, но всё так же ужасный код.

Плагин на систему банка для Bukkit 1.18.2

Зависимости:
  - Essentials
  - PlaceholderAPI
#
#
#

Плагин позволяет: 
  - Вкладывать/снимать физическую валюту с счёта банка или счёта Essentials;
  - Переводить деньги на чужой банковский счёт;
  - Просмотр баланса на счету банка;
  - Налоги на транзакции;
  - Регулярное получение процентов.
#
#
#
Пермишены:
  - axocurrency.help - помощь по плагину. (/bank help)

  - axocurrency.bank.withdraw - снятие монет со счёта. (/bank withdraw <сумма>)
  - axocurrency.bank.deposit - пополнение счёта балансом. (/bank deposit <сумма>)

  - axocurrency.pay - перевод монет со своего баланса на чужой. (/pay <ник> <сумма>)
  - axocurrency.bank.pay - перевод монет со своего счёта на чужой. (/bank pay <ник> <сумма>)

  - axocurrency.bank.money.withdraw - снятие физической валюты со счёта (/bank money withdraw)
  - axocurrency.bank.money.deposit - пополнение физической валютой свой счёт. (/bank money deposit)

  - axocurrency.money.withdraw - снятие физической валюты с баланса. (/money withdraw)
  - axocurrency.money.deposit - пополнение физической валютой свой баланс. (/money deposit)

  - axocurrency.bank.money - просмотр баланса счёта. (/bank money)
  - axocurrency.money - просмотр баланса. (/money)
#
#
#
Админские команды:
  - axocurrency.admin.reload - перезагрузка плагина (/bank admin reload)
  - axocurrency.admin.help - админская помощь по плагину (/bank admin help)
  
  - axocurrency.admin.bank.give - выдать монеты на счёт игрока. (/bank admin give <игрок> <сумма>)
  - axocurrency.admin.bank.take - забрать монеты со счёта игрока. (/bank admin take <игрок> <сумма>)
  - axocurrency.admin.bank.set - установить кол-во монет на счету игрока (/bank admin set <игрок> <сумма>)

  - axocurrency.admin.money.give - выдать монеты на баланс игрока. (/money admin give <игрок> <сумма>)
  - axocurrency.admin.money.take - забрать монеты с баланса игрока. (/money admin take <игрок> <сумма>)
  - axocurrency.admin.money.set - установить кол-во монет на баланс игрока (/money admin set <игрок> <сумма>)

  - axocurrency.admin.bank.money - сколько монет на счету игрока. (/bank admin money <игрок>)
  - axocurrency.admin.money.view - просмотр баланса игрока. (/money admin view <игрок>)
#
#
#
Плейсхолдеры:
  - %axocurrency_bank_money% - монет на счету игрока.
  - %axocurrency_money% - баланс игрока.
  - %axocurrency_taxes_withdraw% - процент налогов на снятие
  - %axocurrency_taxes_deposit% - процент налогов на пополнение
  - %axocurrency_interest_time% - через сколько времени игрок получит проценты.
  - %axocurrency_interest_amount% - предполагаемая сумма, которую игрок должен будет получить с процентов.
