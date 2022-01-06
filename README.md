# birthday-email-sender
Application for sending congratulation messages 
Input:
- recipients.csv
- template folder 
  - with file birthday.html 
  - images folder that contain images
Output:
- sent email

## Kinds of send emails
- mail with congratulations
  - send to person and cc to all
TODO
- mail for manager. Sends one a week/month
# Required
- java 11
- mailutils for ubuntu (and configured postfix)

Useful commands:
- sudo tail -f /var/log/mail.log
