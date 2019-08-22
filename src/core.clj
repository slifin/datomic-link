(ns core
  (:require [clojure.data.json :as json]))

(defn add-item
  "Lambda ion that adds an item, returns database t."
  [{:keys [input]}]
  (let [args (json/read-str input)
        conn (get-connection)]))