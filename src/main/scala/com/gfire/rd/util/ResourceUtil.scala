package com.gfire.rd.util

import java.util.ResourceBundle

import scala.util.control.Exception

/**
  *  ResourceUtil
 *
  * @author chenyl
  * @since 2017-03-08 14:17
 */
object ResourceUtil {

  private var resourceBundle:ResourceBundle = _
  val ZOOKEEPER_HOST = "zookeeper.host"
  val KAFKA_HOST = "kafka.host"
  val KAFKA_TOPIC = "kafka.topic"
  val DATABASE_URL = "database.connectionURL"
  val DATABASE_USERNAME = "database.username"
  val DATABASE_PASSWORD = "database.password"
  val DATABASE_DRIVER_CLASS = "database.driverClass"
  val SPARK_HOST = "spark.host"
  val SOLR_CONFIGPATH = "solr.configpath"
  val SOLR_ADMIN_URL = "solr.admin.url"
  val SOLR_URL = "solr.url"
  val SOLR_NUM_SHARDS = "solr.num.shards"
  val SOLR_NUM_SHREPLICATION_FACTORARDS = "solr.num.shreplication.factorards"
  val SOLR_NUM_SHARDS_PER_NODEARDS = "solr.num.shmax.shards.per.nodeards"
  val HBASE_HOST = "hbase.host"
  val SOLR_STORE_HBASE = "solr.store.hbase"
  val SOLR_QUERY_HBASE = "solr.query.hbase"
  val DRILL_CLUSTER_NAME = "drill.cluster.name"
  val DRILL_ZK_ROOT = "drill.zk.root"
  //indexr
  val INDEXR_HOST = "indexr.host"
  val INDEXR_USER = "indexr.user"
  val INDEXR_PASSWORD = "indexr.password"
  val INDEXR_TOOLS_HOME = "indexr.tools.home"
  val HADOOP_HDFS_HOST = "hadoop.hdfs.host"
  val SPARK_SQL_WAREHOUSE_DIR = "spark.sql.warehouse.dir"
  val SPARK_USER = "spark.sql.user"


  val SPARK_ML_LRMODEL_PATH="spark.ml.lrmodel.path"

  def getConfig(key: String): String = resourceBundle.getString(key)

  try {
        val bigdataConfigFile=System.getProperty("bigdata.config.file")
        if(bigdataConfigFile!=null && !bigdataConfigFile.isEmpty){
            resourceBundle = ResourceBundle.getBundle(bigdataConfigFile)
        }else{
            resourceBundle = ResourceBundle.getBundle("bigdataConfig")

        }
  }catch{
    case ex:Exception=>ex.printStackTrace()
  }

}
