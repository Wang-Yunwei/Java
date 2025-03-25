package need;

import java.security.Permission;
import java.util.Set;

/**
 * @author WangYunwei [2025-03-18]
 */
public class NeedManageController {
    /**
     * 系统管理模块设计
     * <p>
     * 1.用户管理模块
     * 1.1 用户注册/登录/注销
     * 1.2 角色分配与管理
     * 1.3 用户信息维护
     * <p>
     * 2.需求管理模块
     * 2.1需求创建编辑删除
     * 2.2需求状态流转控制
     * 2.3需求版本控制
     * 2.4需求关联关系管理
     * <p>
     * 3.权限控制模块
     * 3.1RBAC权限模型
     * 3.2接口权限验证
     * 3.3数据访问权限控制
     * <p>
     * 4.工作流引擎模块
     * 4.1状态机配置管理
     * 4.2流程审批控制
     * 4.3操作日志记录
     * <p>
     * 5.搜索分析模块
     * 5.1多条件组合查询
     * 5.2需求统计报表
     * 5.3数据可视化展示
     * <p>
     * 6.通知服务模块
     * 6.1站内消息通知
     * 6.2邮件通知服务
     * 6.3操作提醒功能
     *
     * 技术选型
     * - 采用Spring Boot + Spring Security基础框架
     * - Mybatis实现数据持久化操作
     * - ehcache、redis 做缓存
     * - 可用 rabbitMq、mqtt 等消息队列做通知
     * - 集成 Elasticsearch 实现高级检索
     * - 前端可用vue
     *
     * 使用策略模式实现不同审批流程
     * 通过责任链模式处理复杂权限校验
     * 采用工厂模式创建不同通知类型
     * 使用观察者模式实现状态变更事件
     */
    /* ——————用户管理模块伪代码 ————————————*/
    class User {
        Long id;
        String username;
        String password;
        Set<Role> roles;
    }

    class Role {
        Long id;
        String name;
        Set<Permission> permissions;
    }

    // 服务类
    class UserService {
        public User createUser(UserDTO dto) {
            // 密码加密处理
            // 校验用户名唯一性
            // 保存用户信息
        }

        public void assignRoles(Long userId, Set<Long> roleIds) {
            // 验证角色是否存在
            // 更新用户角色关系
        }
    }

    /* ——————需求管理模块伪代码 ————————————*/
    class Requirement {
        Long id;// ID
        String title;// 标题
        String description;//描述
        RequirementStatus status;// 需求状态
        User creator;// 创建人
        User assignee;// 受托人
        LocalDateTime createTime;// 创建时间
        List<VersionHistory> histories;// 历史版本
    }

    enum RequirementStatus {
        DRAFT, REVIEWING, APPROVED, DEVELOPING, TESTING, CLOSED
    }

    class RequirementService {
        public Requirement createRequirement(RequirementDTO dto, User creator) {
            // 校验必填字段
            // 初始化状态为DRAFT
            // 保存版本历史
        }

        public void changeStatus(Long reqId, RequirementStatus newStatus, User operator) {
            // 验证状态流转合法性
            // 检查操作权限
            // 记录状态变更日志
            // 触发通知事件
        }
    }

    /* ——————权限控制模块伪代码 ————————————*/
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface RequirePermission {
        String value();
    }

    class PermissionAspect {
        @Around("@annotation(RequirePermission)")
        public Object checkPermission(ProceedingJoinPoint joinPoint) {
            // 获取当前用户
            // 验证用户是否具备注解声明的权限
            // 无权限则抛出异常
        }
    }

    /* ——————状态标记伪代码 ————————————*/
    public class StateMachine {
        private static final Map<RequirementStatus, Set<RequirementStatus>> TRANSITIONS = Map.of(
                RequirementStatus.DRAFT, Set.of(REVIEWING),
                RequirementStatus.REVIEWING, Set.of(APPROVED, DRAFT),
                RequirementStatus.APPROVED, Set.of(DEVELOPING)
        );

        public static boolean isValidTransition(RequirementStatus current,
                                                RequirementStatus newStatus) {
            return TRANSITIONS.getOrDefault(current, Collections.emptySet())
                    .contains(newStatus);
        }
    }

    /* ——————通知服务伪代码 ————————————*/
    @Service
    public class NotificationService {
        @Async
        public void sendStatusChangeNotification(Requirement req) {
            String content = String.format("需求%s状态变更为%s",
                    req.getId(), req.getStatus());

            // 站内通知
            messageService.send(req.getCreator(), content);
            messageService.send(req.getAssignee(), content);

            // 邮件通知
            emailService.send(req.getCreator().getEmail(),
                    "需求状态更新", content);
        }
    }

    /* ——————搜索服务实现伪代码 ————————————*/
    public interface RequirementRepository extends JpaRepository<Requirement, Long> {
        @Query("SELECT r FROM Requirement r WHERE " +
                "(:title IS NULL OR r.title LIKE %:title%) AND " +
                "(:status IS NULL OR r.status = :status) AND " +
                "(:creatorId IS NULL OR r.creator.id = :creatorId)")
        List<Requirement> searchRequirements(
                @Param("title") String title,
                @Param("status") RequirementStatus status,
                @Param("creatorId") Long creatorId);
    }

    /* ——————数据库设计 ————————————*/
    CREATE TABLE

    users(
            id BIGINT PRIMARY KEY,
            username VARCHAR(50) UNIQUE,

    password VARCHAR(100)
);

    CREATE TABLE

    requirement(
            id BIGINT PRIMARY KEY,
            title VARCHAR(200),

    status VARCHAR(20),

    creator_id BIGINT

    REFERENCES users(id),

    assignee_id BIGINT

    REFERENCES users(id)
);

    CREATE TABLE

    role(
            id BIGINT PRIMARY KEY,
            name VARCHAR(50)
);

    CREATE TABLE

    user_role(
            user_id BIGINT REFERENCES users(id),

    role_id BIGINT

    REFERENCES role(id)
);

}
