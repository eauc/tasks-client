(ns tasks.components.task.list
  (:require [tasks.components.task.sub]
            [tasks.components.task.view :as task-view]
            [tasks.models.tasks :as tasks]
            [re-frame.core :as re-frame]))

(defn toggle-details-id [current new]
  (if (= current new) nil new))

(defn render [tasks {:keys [filter on-filter on-update show-details toggle-details] :as props}]
  [:div.tasks-list
   [:form.tasks-list-filter
    {:on-submit #(-> % .preventDefault)}
    [:input {:type "text"
             :placeholder "Filter"
             :value filter
             :on-change #(on-filter (-> % .-target .-value))}]]
   [:div.tasks-list-body
    (for [task tasks]
      ^{:key (:id task)}
      [task-view/render task
       (merge props {:on-delete #(on-update (tasks/delete-task tasks %))
                     :on-update #(on-update (tasks/update-task tasks %))
                     :show-details (= show-details (:id task))
                     :toggle-details #(toggle-details (toggle-details-id show-details (:id task)))})])]])

(defn component []
  (let [tasks (re-frame/subscribe [:tasks])]
    (fn []
      [render @tasks {}])))
