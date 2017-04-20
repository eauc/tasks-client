(ns tasks.components.task.view)

(defn render-task-view
  [task {:keys [show-body toggle-body]}]
  [:div
   [:input {:type "checkbox"
            :default-checked (:done task)}]
   [:h3 {:on-click toggle-body} (:title task)]
   (if show-body
     [:p (:body task)]
     nil)])
