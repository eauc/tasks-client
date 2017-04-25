(ns tasks.components.task.view
  (:require [tasks.models.task :as task]
            [clojure.string :as str]
            [tasks.debug :as debug]))

(defn render [task {:keys [show-details toggle-details on-delete on-edit on-update]}]
  (let [body (str/trim (:body task))]
    [:div.tasks-view {:class (str (if (:done task) "done") " "
                                  (if show-details "show-details") " "
                                  (if (and show-details
                                           (not (empty? body))) "show-body"))}
     [:div.tasks-view-header
      [:div.tasks-view-done {:on-click #(on-update (task/toggle-done task))}
       [:input {:type "checkbox"
                :checked (:done task)
                :read-only true}]]
      [:p.tasks-view-title {:on-click #(toggle-details task)}
       (:title task)]
      [:button.tasks-view-action {:on-click #(on-edit task)}
       "Edit"]
      [:button.tasks-view-action {:on-click #(on-delete task)}
       "Delete"]]
     [:p.tasks-view-body
      {:dangerouslySetInnerHTML
       {:__html (-> (:body task) str js/marked)}}]]))
