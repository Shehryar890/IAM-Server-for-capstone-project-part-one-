ALTER TABLE platform_admin
    ALTER COLUMN platform_admin_id
    SET DEFAULT gen_random_uuid();

   ALTER TABLE platform_admin
        ALTER COLUMN created_at TYPE TIMESTAMPTZ
        USING created_at AT TIME ZONE 'UTC';

   ALTER TABLE platform_admin
        ALTER COLUMN updated_at TYPE TIMESTAMPTZ
        USING updated_at AT TIME ZONE 'UTC';