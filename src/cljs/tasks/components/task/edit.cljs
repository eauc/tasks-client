(ns tasks.components.task.edit
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.routes :as routes]))

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
       [:input {:type "text"
                :placeholder "Title"
                :on-change #(on-update (assoc-in task [:title] (-> % .-target .-value)))
                :value (:title task)
                :class (if (field-has-problem problems [:title]) "error")}]
       [:textarea.tasks-edit-body
        {:placeholder "Body"
         :on-change #(on-update (assoc-in task [:body] (-> % .-target .-value)))
         :value (:body task)
         :class (if (field-has-problem problems [:body]) "error")}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button"
                  :on-click #(on-cancel task)} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:edit])]
    (fn [_]
      [render @edit
       {:on-update #(re-frame/dispatch [:edit-update %])
        :on-cancel #(routes/nav! routes/home)
        :on-save (fn [task]
                   (re-frame/dispatch [on-save-event task])
                   (routes/nav! routes/home))}])))
