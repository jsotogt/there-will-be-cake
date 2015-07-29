There Will Be Cake
==================
A project for code writers and cake lovers.

Building The Cake
--------------------
* gradle build

Running The Cake Locally
------------------------
* gradle run

Date Format
-----------
* ISO 8601 (e.g. 20080820T080000Z for 2008-08-20 08:00:00)

Endpoints
---------
1. /requests/{id}/{time} (POST)
2. /requests/top (POST)
3. /requests/ids (GET)
4. /requests/{id} (DELETE)
5. /requests/{id}/position (GET)
6. /requests/average/{time} (GET)