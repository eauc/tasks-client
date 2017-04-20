(ns tasks.components.task.view)

(defn render
  [task {:keys [show-details toggle-details toggle-done on-delete on-edit]}]
  [:div.tasks-task {:class (str (if (:done task) "done")
                                " "
                                (if show-details "show-details"))}
   [:div.tasks-task-header
    [:div.tasks-task-done {:on-click #(toggle-done (:id task))}
     [:input {:type "checkbox"
              :checked (:done task)
              :read-only true}]]
    [:h3.tasks-task-title
     {:on-click #(toggle-details (:id task))}
     (:title task)]
    [:button.tasks-task-action
     {:on-click #(on-edit (:id task))}
     "Edit"]
    [:button.tasks-task-action
     {:on-click #(on-delete (:id task))}
     "Delete"]]
   [:p.tasks-task-body (:body task)]])
