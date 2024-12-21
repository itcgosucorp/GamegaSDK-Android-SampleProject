# CHANGE LOG

## Version 1.1.0

### Add New
- Add new module tracking ITS
- Add new list fuction redefine for Gaming Inapp tracking

### Deprecated Functions

The following functions have been deprecated and should no longer be used:

1. **trackingShowSignInSDK**
   - Use `verifySDK()` instead.
2. **trackingSignIn**
   - Use `login(String userId, String userName, String email)` instead.  
3. **doneNRU**
   - Use `createNewCharacter(String serverInfo, String charId, String charName` instead.  
4. **trackingStartTrial**
   - Use `startTutorial(String userId, String charId, String charName, String serverInfo)` instead.  
5. **trackingTutorialCompleted**
   - Use `completeTutorial(String userId, String charId, String charName, String serverInfo)` instead.    
6. **level**
   - Use `levelUp(String charId, String serverInfo, Integer level)` instead.
7. **vip**
   - Use `vipUp(String charId, String serverInfo, Integer vipLevel)` instead.
