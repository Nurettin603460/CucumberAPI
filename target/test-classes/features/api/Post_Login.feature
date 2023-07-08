Feature: You can log in to the system with your email and password

  Scenario: Success Response

    Given Api kullanicisi "api/login" path parametreleri set eder.
    # "api/login" yazarken en başta slash olmamasına dikkat edelim.

    Then Login icin "email" ve "password" girilir.
    # Grup çalışmalarında birden çok email ve password için "email" yerine "nurettinEmail" yazılmalı. Bu nurettinEmail de
    # config properties.te ayrı olarak belirtilmelidir. TAbii ki password için de aynı şeyler geçerlidir.

    Then Login icin Post request gonderilir.