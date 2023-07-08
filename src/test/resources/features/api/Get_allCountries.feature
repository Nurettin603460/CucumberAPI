Feature: It is used to list all the countries registered in the database.

  Scenario: Success Response
    # Varsa başarısız seneryonun da eklenmesi uygun olur.
  # API steplerinde genel ifadeler kullanmaktan kaçınılmalıdır. Tüm API.larda kullanmak için tasarlanmıyorsa.
  # Eğer step tüm API'larda kullanılacak ise genel ifadeler kullanılabilir.

     # ilk adım: spec ve pathparam tanımlamak. bu tüm testlerde kesin olan birşey olduğu için birkez hazırlayacağız ve tüm sorgularda kullanacağız.
    # O yüzden genel bir ifade kullanacağız:

    Given Api kullanicisi "api/profile/allCountries" path parametreleri set eder.

       #Buraya get request.in neye ait olduğunun belirli olması için specific bir ifade kullanılır:
    Then AllCountries icin Get request gonderilir.