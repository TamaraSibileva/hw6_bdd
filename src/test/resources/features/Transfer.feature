#language:ru

  Функциональность: Пополнение карты

    Сценарий: Пополнение первой карты
      Пусть пользователь залогинен с именем "vasya" и паролем "qwerty123"
      Когда пользователь переводит "5000" рублей с карты с номером "5559 0000 0000 0002" на свою карту с номером "5559 0000 0000 0001" с главной страницы
      Тогда баланс его карты с номером "5559 0000 0000 0001" должен стать "15000" рублей

    Сценарий: Пополнение второй карты
      Пусть пользователь залогинен с именем "vasya" и паролем "qwerty123"
      Когда пользователь переводит "8000" рублей с карты с номером "5559 0000 0000 0001" на свою карту с номером "5559 0000 0000 0002" с главной страницы
      Тогда баланс его карты с номером "5559 0000 0000 0002" должен стать "13000" рублей
