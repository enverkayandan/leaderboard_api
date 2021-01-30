Leaderboard API

Notlar:
Scaling problemini çözmek adına leaderboard'u linkedlist olarak implement ettim.
Projede genellikle scaling'e odaklandığım için lock kullanamadım, bu yüzden race conditionlarda db yapısının bozulması olağan.
Db tablosunu oluşturan query "src/main/resources/static" dosyası içindedir.  

#Endpoints#
  /leaderboard (GET) -> returns global leaderboard
  
  /leaderboard/{countryIso} (GET) -> returns country specific leaderboard
  
  /score/submit (POST) : {"userId", "newScore"} -> submits score returns profile of user
  
  /user/create (POST) : {"displayName", "countryIso"} -> creates a new user and returns profile
  
  /user/profile/{userId} (GET) -> returns profile of user
