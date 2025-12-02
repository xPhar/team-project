# Coursework Submission and Grading Platform Team Project

## Project Summary:
A desktop coursework submission and feedback application for windows system that allows users to upload and download assignments, as well as add, remove, and edit grades and feedback for individual or group assignments. The architecture follows Clean Architecture principles to ensure separation of concerns and testability.

## User Stories:
1.	As a student, I want to submit my coding assignment files to the platform so that the instructor can mark them.
2.	As a student, I want to resubmit my work so instructors can mark the latest one.
3.	As a student, I want to check the class average and distribution of each assignment so that I know how good I am compared to peers.
4.	As an instructor, I want to create assignments for students to submit and add instructions so students can submit assignments to specifications.
5.	As an instructor, I want to edit an existing assignment so I can update instructions, deadlines, or requirements if something changes.
6.	As a user, I want to login to the platform using existing credentials so that I can access the platform.
7.	As a user, I want to register for an account and select whether I am a student or an instructor to gain full access to the platform.

## MVPs
| Lead              | Use Case                   | User Story       |
| :---------------- | :------------------------  | :--------------- |
| Aiden             | Login                      | User Story #6    |
| Gracie            | Create/Edit Assignment     | User Story #4，5 ｜  
| Wuqingyi (Cherie) | Show Grade Average         | User Story #3    |
| Jiazheng (Indy)   | Mark Assignment            | User Story #4    |
| Xihang	        | Submit/Resubmit Assignment | User Story #1，2 |
| Turkan Yagmur     | Register                   | User Story #7    |

## Clean Architecture Layers

The project is structured into:
1. Entity Layer — Core business objects
2. Use Case Layer — All application logic
3. Interface Adapter Layer — ViewModels, Controllers, Presenters
4. Framework Layer — Swing UI, API calls, storage handling

## APIs:

**[CSC207 Grade Api](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/documentation/fg3zkjm/5-password-protected-user?entity=request-15617509-f69997c5-cebf-4917-b478-5bbea67bde4b)** \
Used as persistent data storage. Will hold our entities (users, students, classes, assignments) and allow users to get the most up-to-date information.

**[JFreeChart](https://www.jfree.org/jfreechart/)** \
Creates charts and graphs such as bar charts, pie charts, and histograms for data visualization in Java applications. Will allow us to generate grade distribution charts to show users.

## Getting Started
Requirements:
1. Java 17
2. IntelliJ IDEA or equivalent
3. Internet connection (for API access)

Run:
1. Clone repository
2. Open in IntelliJ
3. Run the main application from: src/app/Main.java

## Future Improvements
- Better chart styling: Add more chart types (box plots, trends over time), better color themes, and exportable visual reports.
- Multiple course support: Allow users to manage multiple courses at once with cleaner navigation and course-level dashboards.
- Notification System: Notify users about grading completion, deadlines, regrade status updates, and new assignment creation.
- Role & Permission Management: Add support for TA accounts, co-instructors, and fine-grained permissions for different actions.
- UI/UX Enhancements: Modernize the UI design, improve accessibility, add dark mode, and standardize components across views.
- Bulk Release of Grades: Allow instructors to release all student marks for an assignment simultaneously with one action.
- Regrade Workflow: Implement a full regrade system including student requests, instructor review, approval/denial, and status tracking.
