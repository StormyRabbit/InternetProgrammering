import smtplib
import sys
from smtplib import SMTPException

user = None
password = None
receivers = None
if __name__ == "__main__":
    if password is None or user is None:
        sys.exit("Password or User is missing, edit py file to add")

    receivers = input("Enter email adress of recievers (' ' to divide):")
    receivers = receivers.split(' ')
    subject = input("Enter Subject: ")
    text = input("Enter message: ")
    message = 'Subject: {}\n\n{}'.format(subject, text)
    try:
        smtp_obj = smtplib.SMTP('smtp.gmail.com:587')
        smtp_obj.ehlo()
        smtp_obj.starttls()
        smtp_obj.login(user, password)
        smtp_obj.sendmail(user, receivers, message.encode("utf8"))
        print("\n Message sent from: ", user )
        print("\n Message sent to: ", receivers )
        print("\n\n%s", message )
        smtp_obj.quit()
    except SMTPException:
        print("SMTPException in init")
