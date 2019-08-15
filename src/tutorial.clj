(ns tutorial
  (:require [datomic.client.api :as d]))

(defn make-idents [x]
  (mapv #(hash-map :db/ident %) x))

(comment
  (def cfg {
            :server-type :ion
            :region "eu-west-1"
            :system "personal-datomic"
            :endpoint "http://entry.personal-datomic.eu-west-1.datomic.net:8182"
            :proxy-port 8182})
  (def client (d/client cfg))
  (def conn (d/connect client {:db-name "tutorial"})))


(comment
  (d/create-database client {:db-name "tutorial"})
  (def conn (d/connect client {:db-name "tutorial"}))
  (def colours [:red :green :blue :yellow])
  (def sizes [:small :medium :large :xlarge])
  (def types [:shirt :pants :dress :hat])
  (d/transact conn {:tx-data (make-idents colours)})
  (d/transact conn {:tx-data (make-idents sizes)})
  (d/transact conn {:tx-data (make-idents types)}))


(comment
  (def schema-1
    [{:db/ident :inv/sku
      :db/valueType :db.type/string
      :db/unique :db.unique/identity
      :db/cardinality :db.cardinality/one}
     {:db/ident :inv/color
      :db/valueType :db.type/ref
      :db/cardinality :db.cardinality/one}
     {:db/ident :inv/size
      :db/valueType :db.type/ref
      :db/cardinality :db.cardinality/one}
     {:db/ident :inv/type
      :db/valueType :db.type/ref
      :db/cardinality :db.cardinality/one}])
  (d/transact conn {:tx-data schema-1}))


(defn create-sample-data [colors sizes types]
  "Create a vector of maps of all permutations of args"
  (->> (for [color colors size sizes type types]
         {:inv/color color
          :inv/size size
          :inv/type type})
       (map-indexed
         (fn [idx map]
           (assoc map :inv/sku (str "SKU-" idx))))
       vec))
(comment
  @(def sample-data
    (create-sample-data colours sizes types))
  (d/transact conn {:tx-data sample-data}))