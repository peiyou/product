
import com.btcc.build.sql.SqlContext
import spock.lang.Specification


/**
 * Created by peiyou on 2016/11/24.
 */
class SqlContextSpec extends Specification {
    def "SqlContextSpec of result"(){
        when: "a new SqlContext"
        def map = new HashMap<String,Object>();
        map.put("name","张");
        map.put("age",11);
        // map.put("num",1);
        def sql = 'select * from xxx where 1=1' +
                '#if[name != null] and name = \\${name}!#if' +
                '#if[age != null] and age = \\${age}!#if' +
                '#if[num != null] and num = \\${num} !#if';
        def sqlContext = new SqlContext(sql,map)

        then:"result select * from xxx where 1=1 and name = \${name} and age = \${age}"
        sqlContext.getSql() == 'select * from xxx where 1=1 and name = \\${name} and age = \\${age}';
    }
}
