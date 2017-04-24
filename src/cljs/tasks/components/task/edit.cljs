(ns tasks.components.task.edit
  (:require [cljs.spec :as spec]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.routes :as routes]
            [tasks.utils :as utils]))

(defn problem-for-field [problems field]
  (filter #(= field (:path %)) problems))

(defn field-has-problem [problems field]
  (not (empty? (problem-for-field problems field))))

(defn render [task {:keys [on-cancel on-update on-save]}]
  (let [valid (spec/valid? :tasks.models.task/task task)
        problems (:cljs.spec/problems (spec/explain-data :tasks.models.task/task task))]
    [:form {:class (if (not valid) "error")
            :on-submit (fn [event]
                         (.preventDefault event)
                         (on-save task))}
     [:fieldset
      [:legend "Edit Task"]
      [:div.tasks-edit
       [form-input/render :input
        {:autoFocus true
         :has-error (field-has-problem problems [:title])
         :on-update #(on-update (assoc-in task [:title] %))
         :placeholder "Title"
         :type "text"
         :value (:title task)}]
       [form-input/render :textarea
        {:class "tasks-edit-body"
         :has-error (field-has-problem problems [:body])
         :on-update #(on-update (assoc-in task [:body] %))
         :placeholder "Body"
         :value (:body task)}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button"
                  :on-click #(on-cancel task)} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:edit])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event %])
        on-update (utils/debounce (fn [task] (re-frame/dispatch [:edit-update task])) 250)]
    (fn [_]
      [render @edit
       {:on-cancel on-cancel
        :on-save on-save
        :on-update on-update}])))
