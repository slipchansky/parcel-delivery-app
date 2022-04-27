from api_framework.api_framework import BaseApiFramework

api_prefix = "/admin"

couriers = "/couriers"
couriers_free= couriers+"/free"
couriers_busy= couriers+"/busy"
couriers_one = couriers+"/{id}"

tasks = "/tasks"
task_one = tasks+"/{id}"
tasks_unassigned = tasks+"/unassigned"
task_assign = task_one+"/to/{courier_id}"

class AdminApi(BaseApiFramework):
    def __init__(self, base_url, username, password):
        BaseApiFramework.__init__(self, base_url, api_prefix, username, password)


    def new_courier(self, courier_id):
        return self.post(couriers_one, id=courier_id)

    def get_couriers(self):
        return self.get(couriers)

    def get_couriers_free(self):
        return self.get(couriers_free)

    def get_tasks(self):
        return self.get(tasks)

    def get_tasks_unassigned(self):
        return self.get(tasks_unassigned)

    def get_task(self, id):
        return self.get(task_one, id=id)

    def assign_task(self, id, to):
        return self.put(task_assign, id=id, courier_id=to)







