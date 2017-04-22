(ns tasks.components.task.list
  (:require [tasks.components.task.create :as task-create]
            [tasks.components.task.handler]
            [tasks.components.task.sub]
            [tasks.components.task.view :as task-view]
            [tasks.models.tasks :as tasks]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [tasks.routes :as routes]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "orange")}

(defn toggle-details-id [current new]
  (if (= current new) nil new))

(defn filter-render [filter {:keys [on-filter]}]
  [:form.tasks-list-filter
   {:on-submit #(-> % .preventDefault)}
   [:input {:type "text"
            :placeholder "Filter"
            :value filter
            :on-change #(on-filter (-> % .-target .-value))}]])

(defn render [tasks {:keys [filter on-update show-details toggle-details] :as props}]
  [:div.tasks-list
   [task-create/component]
   [filter-render filter props]
   [:div.tasks-list-body
    (for [task tasks]
      ^{:key (:id task)}
      [task-view/render task
       (merge props {:on-delete #(on-update (tasks/delete-task tasks %))
                     :on-update #(on-update (tasks/update-task tasks %))
                     :show-details (= show-details (:id task))
                     :toggle-details #(toggle-details (toggle-details-id show-details (:id task)))})])]])

(defn component []
  (let [filter (re-frame/subscribe [:filter])
        show-details (re-frame/subscribe [:show-details])
        tasks (re-frame/subscribe [:tasks-sorted])]
    (fn component-render []
      [render @tasks
       {:filter @filter
        :on-edit #(routes/nav! routes/edit {:id (:id %)})
        :on-filter #(re-frame/dispatch [:filter-update %])
        :on-update #(re-frame/dispatch [:tasks-update %])
        :show-details @show-details
        :toggle-details #(re-frame/dispatch [:toggle-details %])}])))

;; )
