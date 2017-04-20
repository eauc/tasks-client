(ns tasks.components.task.edit)

(defn render [task {:keys [on-cancel on-update on-save]}]
  [:form {:on-submit (fn [event]
                       (.preventDefault event)
                       (on-save task))}
   [:fieldset
    [:legend "Edit Task"]
    [:div.tasks-task-edit
     [:input {:type "text"
              :placeholder "Title"
              :on-change #(on-update (assoc-in task [:title] (-> % .-target .-value)))
              :value (:title task)}]
     [:textarea.tasks-task-edit-body
      {:placeholder "Body"
       :on-change #(on-update (assoc-in task [:body] (-> % .-target .-value)))
       :value (:body task)}]
     [:div
      [:button {:type "submit"} "Save"]
      [:button {:type "button"
                :on-click #(on-cancel task)} "Cancel"]]]]])
