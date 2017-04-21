(ns tasks.components.task.view
  (:require [tasks.models.task :as task]))

(defn render [task {:keys [show-details toggle-details on-delete on-edit on-update]}]
  [:div.tasks-view {:class (str (if (:done task) "done") " "
                                (if show-details "show-details"))}
   [:div.tasks-view-header
    [:div.tasks-view-done {:on-click #(on-update (task/toggle-done task))}
     [:input {:type "checkbox"
              :checked (:done task)
              :read-only true}]]
    [:h3.tasks-view-title {:on-click #(toggle-details task)}
     (:title task)]
    [:button.tasks-view-action {:on-click #(on-edit task)}
     "Edit"]
    [:button.tasks-view-action {:on-click #(on-delete task)}
     "Delete"]]
   [:p.tasks-view-body (:body task)]])
