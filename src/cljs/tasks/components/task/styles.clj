(ns tasks.components.task.styles)

(def edit
	[:&-edit {:display "flex"
						:flex-flow "column nowrap"}
	 [:&-body {:min-height "8em"}]])

(def list
	[:&-list
   [:&-filter {:display "flex"
               :flex-flow "column nowrap"}]
   [:.tasks-view {:border-bottom "1px solid rgba(0,0,0,0.2)"}]])

(def view
	[:&-view {:user-select "none"}
	 [:&-header {:display "flex"
							 :flex-flow "row nowrap"
							 :align-items "center"}]
	 [:&.done [:.tasks-view-title {:opacity 0.6
																 :font-style "italic"
																 :text-decoration "line-through"}]]
	 [:&.show-details
		[:.tasks-view-action {:display "block"}]
		[:.tasks-view-body {:display "block"}]]
	 [:&-action {:display "none"
							 :background-color "white"
							 :border "1px solid rgba(0,0,0,0.5)"
							 :border-radius "3px"
							 :margin "0 0.5em"
							 :padding "0.5em 0.8em"}]
	 [:&-body {:display "none"}]
	 [:&-done [:input {:margin "0.75em"}]]
	 [:&-title {:flex-grow 1
							:cursor "pointer"
							:margin "0.5em 0"}]])
