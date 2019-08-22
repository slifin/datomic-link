(ns core
  (:require [clojure.data.json :as json]
            [datomic.client.api :as d]))

(defn create-item
  "Transaction fn that creates data to make a new item"
  [db sku size color type]
  [{:inv/sku sku
    :inv/color (keyword color)
    :inv/size (keyword size)
    :inv/type (keyword type)}])

(defn add-item
  "Lambda ion that adds an item, returns database t."
  [{:keys [input]}]
  (let [args (json/read-str input)
        conn (get-connection)
        tx [(list* 'core/create-item args)]
        result (d/transact conn {:tx-data tx})]
      (pp-str {:t (-> result :db-after :t)})))