(ns tasks.components.task.list
  (:require [tasks.components.task.view :as task-view]
            [tasks.models.tasks :as tasks]))

(defn render [tasks {:keys [on-update show-details] :as props}]
  [:div.tasks-list
   (for [task tasks]
     ^{:key (:id task)}
     [task-view/render task
      (merge props {:on-delete #(on-update (tasks/delete-task tasks %))
                    :on-update #(on-update (tasks/update-task tasks %))
                    :show-details (= show-details (:id task))})])])
