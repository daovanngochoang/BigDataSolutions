# Tracking parking slot notes.
1. should have an object that represent the parking slots.
2. there are event 
   1. entry. 
   2. parked. 
   3. moving. 
   4. out
3. object tracking in case there are noise such as human, cars by past, ... that lead to the re-detect
   then the object is changed, and it has a new id => check the time and events flow.
   - time too short and event is not follow the event flow => the new object id should be updated 
```bash

```