Leaderboard API

Notlar:
Scaling problemini çözmek adına leaderboard'u linkedlist olarak implement ettim.
Projede genellikle scaling'e odaklandığım için lock kullanmadım, aynı anda birden fazla submitScore isteği geldiğinde db yapısı bozulabilir.
Db tablosunu oluşturan query "src/main/resources/static" dosyası içindedir.  

#Endpoints#
  /leaderboard (GET) -> returns global leaderboard
  
  /leaderboard/{countryIso} (GET) -> returns country specific leaderboard
  
  /score/submit (POST) : {"userId", "newScore", "timestamp"} -> submits score returns profile of user
  
  /user/create (POST) : {"displayName", "countryIso"} -> creates a new user and returns profile
  
  /user/profile/{userId} (GET) -> returns profile of user
