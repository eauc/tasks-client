(ns tasks.components.task-edit.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.components.task-edit.handler]
            [tasks.components.task-edit.sub]
            [tasks.routes :as routes]
            [tasks.utils :as utils]))

(defn render [task {:keys [errors on-cancel on-update on-save]}]
  (let [on-save-debounce (utils/debounce on-save 250)
        on-submit (fn [event]
                    (.preventDefault event)
                    (on-save-debounce))]
    [:form {:class (if-not (empty? errors) "error")
            :on-submit on-submit}
     [:fieldset
      [:legend "Edit Task"]
      [:div.tasks-edit
       [form-input/render :input
        {:autoFocus true
         :error (:title errors)
         :field [:title]
         :label "Title"
         :on-update on-update
         :type "text"
         :value (:title task)}]
       [form-input/render :textarea
        {:class "tasks-edit-body"
         :error (:body errors)
         :field [:body]
         :label "Body"
         :on-update on-update
         :value (:body task)}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button" :on-click on-cancel} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [task-edit (re-frame/subscribe [:task-edit])
        errors (re-frame/subscribe [:task-edit-errors])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event])
        on-update #(re-frame/dispatch [:task-edit-update %1 %2])]
    (fn task-edit-component [_]
      [render @task-edit
       {:errors @errors
        :on-cancel on-cancel
        :on-save on-save
        :on-update on-update}])))
