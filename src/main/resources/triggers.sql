
IF EXISTS (
        SELECT * FROM sys.triggers
        WHERE [name] = 'products_trigger'
    )
    DROP TRIGGER [19118028].[products_trigger];

CREATE TRIGGER [19118028].[products_trigger]
    ON [19118028].[products]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'products')
        END

    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'products')
        END
END;


IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'orders_trigger'
        )
        DROP TRIGGER [19118028].[orders_trigger];

CREATE TRIGGER [19118028].[orders_trigger]
    ON [19118028].[orders]
    AFTER INSERT, UPDATE
    AS
BEGIN

    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'orders')
        END
        -- Check if this is an UPDATE operation
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'orders')
        END
END;



IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'users_trigger'
        )
        DROP TRIGGER [19118028].[users_trigger];

CREATE TRIGGER [19118028].[users_trigger]
    ON [19118028].[users]
    AFTER INSERT, UPDATE
    AS
BEGIN

    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'users')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'users')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'roles_trigger'
        )
        DROP TRIGGER [19118028].[roles_trigger];

CREATE TRIGGER [19118028].[roles_trigger]
    ON [19118028].[roles]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'roles')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'roles')
        END
END;


IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'users_roles_trigger'
        )
        DROP TRIGGER [19118028].[users_roles_trigger];

CREATE TRIGGER [19118028].[users_roles_trigger]
    ON [19118028].[users_roles]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'users_roles')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'users_roles')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'addresses_trigger'
        )
        DROP TRIGGER [19118028].[addresses_trigger];

CREATE TRIGGER [19118028].[addresses_trigger]
    ON [19118028].[addresses]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'addresses')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'addresses')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'blogs_trigger'
        )
        DROP TRIGGER [19118028].[blogs_trigger];

CREATE TRIGGER [19118028].[blogs_trigger]
    ON [19118028].[blogs]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'blogs')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'blogs')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'blog_category_trigger'
        )
        DROP TRIGGER [19118028].[blog_category_trigger];

CREATE TRIGGER [19118028].[blog_category_trigger]
    ON [19118028].[blog_category]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'blog_category')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'blog_category')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'comments_trigger'
        )
        DROP TRIGGER [19118028].[comments_trigger];

CREATE TRIGGER [19118028].[comments_trigger]
    ON [19118028].[comments]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'comments')
        END
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'comments')
        END
END;

IF EXISTS (
            SELECT * FROM sys.triggers
            WHERE [name] = 'product_categories_trigger'
        )
        DROP TRIGGER [19118028].[product_categories_trigger];

CREATE TRIGGER [19118028].[product_categories_trigger]
    ON [19118028].[product_categories]
    AFTER INSERT, UPDATE
    AS
BEGIN
    IF EXISTS(SELECT * FROM inserted) AND NOT EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'INSERT', 'product_categories')
        END
        -- Check if this is an UPDATE operation
    ELSE IF EXISTS(SELECT * FROM inserted) AND EXISTS(SELECT * FROM deleted)
        BEGIN
            INSERT INTO [19118028].[log_19118028] ("date_time", "type", "name")
            VALUES (GETDATE(), 'UPDATE', 'product_categories')
        END
END;


