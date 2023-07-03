# Issue_Tracker
Issue Tracking System

### Installation

Run `docker-compose up -d` in the main folder where the `compose.yaml` file is located. The app runs on `http://localhost:3000/`.

### Users

admin
user
viewer

(the **password** for simplicity's sake is uniformly **admin**)

### About project

This is a basic (Minimum Viable Product) issue tracking system, that is used for tracking tickets, be it a bug, feature or anything alike.

###The features include

General:

- role management
- exception handling, tells the user if something's wrong using a modal

Header:

- notifications, if someone other than the assigner is assigned a ticket they are notified by the bell and the number of tickets assigned, the new tickets are also highlighted by an orange background color until they are clicked on by the assignee
- create new ticket function, the user with admin/user role can create a new ticket, this function is not available for the viewer
- tickets, a datatable with the existing tickets
- login/logout, self-explanatory

All tickets view:

- choosing between seeing all the tickets or only the ones assigned to you
- filtering results by field
- ordering results by field
- clicking on a ticket will redirect you to the single ticket view page
- admin can delete tickets
- you can differentiate the priority of the comments at a glance, thanks to the priority icons

Single ticket view page:

- inline editing of issue name/description (the selected project is final and can not be changed)
- changing assignees, ticket status/type/priority
- adding new files to the ticket, clicking on a file will initiate the download, admin can delete files
- comment system, you are able to add comments, and the user who wrote the comment can edit/delete it, this is the only feature available to the viewer
