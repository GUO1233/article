package cn.itcast.article.service;

import cn.itcast.article.dao.CommentRepository;
import cn.itcast.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    //注入dao
    @Autowired
    private CommentRepository commentRepository;//注入代理对象 SimpleMongoRepository

    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 保存一个评论
     * @param comment
     */
    public void saveComment(Comment comment){
    //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
    //设置一些默认初始值。。。
    //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     * @param comment
     */
    public void updateComment(Comment comment){
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     * @param id
     */
    public void deleteCommentById(String id){
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     * @return
     */
    public List<Comment> findCommentList(){
        return commentRepository.findAll();
    }

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    public Comment findCommentById(String id){
        return commentRepository.findById(id).get();
    }

    /**
     * 根据父id查询分页列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Comment> findCommentListPageByParentid(String parentid, int page , int size){
        return commentRepository.findByParentid(parentid, PageRequest.of(page-1,size));
    }

    /**
     * 点赞数+1
     * @param id
     */
    public void updateCommentLikenum(String id){
        System.out.println("github测试3");
        System.out.println("github测试2");
        System.out.println("github测试1");
        //查询对象
        Query query= Query.query(Criteria.where("_id").is(id));
        //更新对象
        Update update=new Update();
        //局部更新，相当于$set
        // update.set(key,value)
        //递增$inc
        // update.inc("likenum",1);//第二个参数是步长
        update.inc("likenum");
        //参数1：查询对象
        //参数2：更新对象
        //参数3：集合的名字或实体类的类型Comment.class
        mongoTemplate.updateFirst(query,update,"comment");//第三个参数也可以用：Comment.class
    }


}
