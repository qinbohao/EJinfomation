package main.java;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import sql.java.Info;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class infoPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO �Զ����ɵķ������
		Info info=(Info)resultItems.get("info");
		String resource = "conf.xml";
	        //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	    InputStream is = EjinfoProcessor.class.getClassLoader().getResourceAsStream(resource);
	        //����sqlSession�Ĺ���
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	        //ʹ��MyBatis�ṩ��Resources�����mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	        //Reader reader = Resources.getResourceAsReader(resource); 
	        //����sqlSession�Ĺ���
	        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        //������ִ��ӳ���ļ���sql��sqlSession
	    SqlSession session = sessionFactory.openSession();
	        /**
	         * ӳ��sql�ı�ʶ�ַ�����
	         * me.gacl.mapping.userMapper��userMapper.xml�ļ���mapper��ǩ��namespace���Ե�ֵ��
	         * getUser��select��ǩ��id����ֵ��ͨ��select��ǩ��id����ֵ�Ϳ����ҵ�Ҫִ�е�SQL
	         */
	    String statement = "sql.mybatis.Infomapper.addInfo";//ӳ��sql�ı�ʶ�ַ���
	        //ִ�в�ѯ����һ��Ψһuser�����sql
	     session.insert(statement, info);
	     session.close();
	}

}
