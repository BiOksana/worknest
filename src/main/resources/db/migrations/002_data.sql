-- liquibase formatted sql
-- changeset oksana.bibikova:002

INSERT INTO project (name, field, experience, deadline, description, status)
VALUES
('Creating visual materials for social media', 'Design', 'MORE_THREE_YEARS', '2025-11-22',
  'We are looking for a creative and detail-oriented designer to develop eye-catching and engaging visual materials for our social media platforms.', 'ACTIVE'),
('Building internal HR management tool', 'Software Development', 'MORE_TWO_YEARS', '2025-12-10',
  'We are looking for a full-stack developer to create an internal web application to manage employee records, vacations, and performance reviews.', 'ACTIVE'),
('Creating marketing visuals for holiday campaign', 'Design', 'MORE_ONE_YEAR', '2025-11-05',
  'Join us to design promotional banners, social media assets, and landing page graphics for our upcoming winter holiday campaign.', 'ACTIVE'),
('Developing e-learning modules for remote teams', 'Instructional Design', 'MORE_THREE_YEARS', '2025-12-20',
  'We need an instructional designer to create engaging and interactive e-learning content for onboarding and upskilling remote employees.', 'ACTIVE'),
('Improving checkout flow on e-commerce website', 'UX Research', 'MORE_TWO_YEARS', '2024-06-15',
  'A UX researcher was tasked with identifying bottlenecks in the checkout funnel and proposing data-backed interface improvements.', 'PASSED'),
('Translating website content for international launch', 'Localization', 'MORE_ONE_YEAR', '2024-05-30',
  'This project focused on adapting all content for Spanish- and German-speaking markets, including UI text, FAQs, and legal pages.', 'PASSED'),
('Analyzing research and providing ideas for improving product', 'Marketing', 'MORE_ONE_YEAR', '2024-06-01',
  'We need a marketing expert to analyze user behavior and improve product design based on data-driven decisions.', 'PASSED');

INSERT INTO vacancy (project_id, name, field, experience, country, description) VALUES
(1, 'NLP Engineer', 'Software Development', 'MORE_THREE_YEARS', 'Germany',
 'Looking for an NLP specialist to develop and fine-tune chatbot language models using spaCy and HuggingFace.'),
(1, 'Frontend Developer', 'Software Development', 'MORE_TWO_YEARS', 'Poland',
 'Implement user-facing components and integrate chatbot into the existing React application.'),
(2, 'UX Designer', 'Design', 'MORE_TWO_YEARS', 'Germany',
 'Improve wireframes and design prototypes for a banking app with a strong focus on accessibility and UX.'),
(2, 'Design QA Tester', 'Design', 'MORE_ONE_YEAR', 'Ukraine',
 'Ensure consistency in design implementation across Android/iOS platforms.'),
(3, 'Marketing Specialist', 'Marketing', 'MORE_ONE_YEAR', 'Spain',
 'Assist in planning and executing multi-channel marketing strategy, including email and PPC campaigns.'),
(3, 'Copywriter', 'Marketing', 'MORE_ONE_YEAR', 'Germany',
 'Create compelling marketing texts, blog posts, and social media content for product promotion.'),
(4, 'Cloud Architect', 'DevOps', 'MORE_THREE_YEARS', 'Germany',
 'Design AWS infrastructure for high availability and security for enterprise apps.'),
(4, 'DevOps Engineer', 'DevOps', 'MORE_THREE_YEARS', 'Poland',
 'Automate deployment pipeline using Terraform and GitHub Actions.'),
(5, 'Unity Developer', 'Game Development', 'MORE_TWO_YEARS', 'Ukraine',
 'Implement core gameplay mechanics using Unity and C#.'),
(5, 'Content Designer', 'Game Development', 'MORE_ONE_YEAR', 'Spain',
 'Design educational scenarios and vocab content for German language learning.');