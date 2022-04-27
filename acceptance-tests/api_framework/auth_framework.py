import json
import time

import requests


auth_login = '/api/v1/auth/login'
auth_newclient = '/api/v1/auth/signup'

AUTH_API_ACCESS_TOKEN_RETRIES=3
SLEEP_INTERVAL=1 




class AuthAPIFramework(object):
    def __init__(self, base_url):
        self.base_url = base_url

    def create_token(self, user, password):

        headers = {
            'Content-Type': 'application/json'
        }

        tries_count = 0
        token_received = False
        auth_url = self.base_url + auth_login
        data = {
          'username': user,
          'password': password
        }
        while tries_count < AUTH_API_ACCESS_TOKEN_RETRIES:
            response_json = requests.post(auth_url,
                                          headers=headers,
                                          json=data)

            response_content = json.loads(response_json.content)
            if response_json.status_code == 200:
                token_received = True
                return True, "Bearer "+response_content['token']
            else:
                print(f'Auth-API responded with status_code {response_json.status_code}. Let us try again!')
                tries_count += 1
                time.sleep(SLEEP_INTERVAL)

        return False, None

