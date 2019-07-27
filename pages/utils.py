import json

from django.http import HttpResponse
from django.contrib.sessions.models import Session

def formulate_response(message, success, code, data=None):
    if data is None:
        resp_dict = {"success": success, "message": message}
    else:
        resp_dict = {"success": success, "message": message, "data": data}

    response = HttpResponse(
        json.dumps(resp_dict),
        content_type="application/json"
    )

    response.status_code = code
    return response

# returns a user for the current session
def session_validation(session_key):

    session = Session.objects.filter(session_key=session_key)
    # print("the sessions\n", session, type(session))
    if len(session) < 1:
        return None
    session_data = session[0].get_decoded()
    # print("data", session_data)
    if "user_email" not in session_data:
        # print("empty session data")
        return None
    return session_data['user_email']