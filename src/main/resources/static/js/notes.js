import { fetchWithAuth } from './auth.js';
import { Timer } from './timer.js';
import { logout } from './auth.js';

const API_BASE_URL = '/api';

// Фиксированные ID TimeBlock'ов (должны соответствовать вашей БД)
const TIME_BLOCK_MAP = {
    DAY: { id: 1, type: 'DAY' },
    WEEK: { id: 2, type: 'WEEK' },
    MONTH: { id: 3, type: 'MONTH' }
};

let currentTimeType = 'DAY';
let currentTaskId = null;

// Инициализация таймера
const timer = new Timer(document.getElementById('timer-display'));
document.getElementById('start-timer').addEventListener('click', () => timer.start());
document.getElementById('pause-timer').addEventListener('click', () => timer.pause());
document.getElementById('reset-timer').addEventListener('click', () => timer.reset());

// Логика выхода
document.getElementById('logout-btn').addEventListener('click', logout);

// Элементы модального окна
const modal = document.getElementById('note-modal');
const saveBtn = document.getElementById('save-note-btn');
const cancelBtn = document.getElementById('cancel-note-btn');
const titleInput = document.getElementById('note-title');
const textInput = document.getElementById('note-text');
const tagSelect = document.getElementById('note-tag');

// Функции для работы с задачами
async function createTask(taskData) {
    const timeBlock = TIME_BLOCK_MAP[taskData.timeBlockType];

    const response = await fetchWithAuth(`${API_BASE_URL}/tasks`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: taskData.title,
            description: taskData.description,
            tag: taskData.tag,
            timeBlockId: timeBlock.id
        })
    });
    return await response.json();
}

async function getTasks() {
    const response = await fetchWithAuth(`${API_BASE_URL}/tasks`);
    const tasks = await response.json();

    // Добавляем информацию о timeBlock к каждой задаче
    return tasks.map(task => {
        // Ищем TimeBlock по типу (а не по ID)
        const timeBlockKey = Object.keys(TIME_BLOCK_MAP).find(
            key => TIME_BLOCK_MAP[key].type === task.timeBlockType
        );
        return {
            ...task,
            timeBlock: TIME_BLOCK_MAP[timeBlockKey]
        };
    });
}

async function updateTask(id, taskData) {
    const timeBlock = TIME_BLOCK_MAP[taskData.timeBlockType];

    const response = await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: taskData.title,
            description: taskData.description,
            tag: taskData.tag,
            timeBlockId: timeBlock.id
        })
    });
    return await response.json();
}

async function deleteTask(id) {
    await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, {
        method: 'DELETE'
    });
}

function renderTasks(tasks) {
    document.querySelectorAll('.notes').forEach(container => {
        container.innerHTML = '';
    });

    tasks.forEach(task => {
        console.log('Task:', task.id, 'TimeBlock:', task.timeBlock); // Добавлено здесь
        const timeBlockType = task.timeBlock?.type || 'DAY';
        const container = document.getElementById(`${timeBlockType.toLowerCase()}-notes`);
        if (container) {
            container.appendChild(createTaskElement(task));
        }
    });
}

function createTaskElement(task) {
    const element = document.createElement('div');
    element.className = 'note';
    element.dataset.id = task.id;
    element.innerHTML = `
        <strong>${task.title}</strong>
        <p>${task.description}</p>
        <p>Tag: ${task.tag}</p>
        <p>Type: ${task.timeBlock?.type || 'DAY'}</p>
        <div class="note-actions">
            <button class="note-delete">×</button>
            <button class="note-edit">✏️</button>
        </div>
    `;

    element.querySelector('.note-delete').addEventListener('click', async () => {
        await deleteTask(task.id);
        const tasks = await getTasks();
        renderTasks(tasks);
    });

    element.querySelector('.note-edit').addEventListener('click', () => {
        openEditModal(task);
    });

    return element;
}

function openAddModal(timeType) {
    currentTimeType = timeType;
    currentTaskId = null;
    titleInput.value = '';
    textInput.value = '';
    tagSelect.value = 'MAIN';
    modal.classList.add('show');
}

function openEditModal(task) {
    currentTaskId = task.id;
    currentTimeType = task.timeBlock?.type || 'DAY';
    titleInput.value = task.title;
    textInput.value = task.description;
    tagSelect.value = task.tag || 'MAIN';
    modal.classList.add('show');
}

function closeModal() {
    modal.classList.remove('show');
}

async function saveNote() {
    const title = titleInput.value.trim();
    const description = textInput.value.trim();
    const tag = tagSelect.value;

    if (!title || !description) {
        alert('Please fill all fields');
        return;
    }

    try {
        const taskData = {
            title,
            description,
            tag,
            timeBlockType: currentTimeType
        };

        if (currentTaskId) {
            await updateTask(currentTaskId, taskData);
        } else {
            await createTask(taskData);
        }

        closeModal();
        const tasks = await getTasks();
        renderTasks(tasks);
    } catch (error) {
        console.error('Error saving note:', error);
        alert('Failed to save note: ' + error.message);
    }
}

// Инициализация
document.addEventListener('DOMContentLoaded', async () => {
    if (!localStorage.getItem('jwt')) {
        window.location.href = '/login.html';
        return;
    }

    // Назначение обработчиков для кнопок добавления
    document.querySelectorAll('.add-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const timeType = btn.closest('.tab').dataset.section;
            openAddModal(timeType);
        });
    });

    // Обработчики модального окна
    saveBtn.addEventListener('click', saveNote);
    cancelBtn.addEventListener('click', closeModal);

    // Загрузка задач
    try {
        const tasks = await getTasks();
        renderTasks(tasks);
    } catch (error) {
        console.error('Failed to load tasks:', error);
    }
});