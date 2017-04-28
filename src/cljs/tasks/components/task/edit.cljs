(ns tasks.components.task.edit
  (:require [cljs.spec :as spec]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.routes :as routes]
            [tasks.utils :as utils]))

(defn render [task {:keys [on-cancel on-update on-save]}]
  (let [on-save-debounce (utils/debounce on-save 250)
        on-submit (fn [event]
                    (.preventDefault event)
                    (on-save-debounce))
        valid (spec/valid? :tasks.models.task/task task)]
    [:form {:class (if (not valid) "error")
            :on-submit on-submit}
     [:fieldset
      [:legend "Edit Task"]
      [:div.tasks-edit
       [form-input/render :input
        {:autoFocus true
         :field [:title]
         :on-update on-update
         :placeholder "Title"
         :spec :tasks.models.task/title
         :type "text"
         :value (:title task)}]
       [form-input/render :textarea
        {:class "tasks-edit-body"
         :field [:body]
         :on-update on-update
         :placeholder "Body"
         :spec :tasks.models.task/body
         :value (:body task)}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button" :on-click on-cancel} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:edit])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event])
        on-update #(re-frame/dispatch [:edit-update %1 %2])]
    (fn [_]
      [render @edit
       {:on-cancel on-cancel
        :on-save on-save
        :on-update on-update}])))
