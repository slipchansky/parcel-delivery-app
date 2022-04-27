from api_framework.api_framework import BaseApiFramework


api_prefix = "/courier"

tasks = "/tasks"
task_one = tasks+"/{id}"

class CourierApi(BaseApiFramework):
    def __init__(self, base_url, username, password):
        BaseApiFramework.__init__(self, base_url, api_prefix, username, password)


    def get_tasks(self):
        return self.get(tasks)

    def get_task(self, id):
        return self.get(task_one, id=id)

    def start_task(self, id):
        return self.post(task_one, id=id)

    def set_location(self, id, location):
        return self.put(task_one, id=id, location=location)

    def task_complete(self, id):
        return self.delete(task_one, id=id)
